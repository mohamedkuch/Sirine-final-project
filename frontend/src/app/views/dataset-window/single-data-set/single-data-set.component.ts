import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataSetService } from '../../../services/data-set.service';
import * as d3 from 'd3';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-single-data-set',
  templateUrl: './single-data-set.component.html',
  styleUrls: ['./single-data-set.component.css'],
})
export class SingleDataSetComponent implements OnInit {
  id: string;
  item: any;
  itemKeys: string[] = [];
  itemValues: [] = [];
  selectedKey: string = '';
  selectedValue: string = '';
  treeMapData: any;

  constructor(
    private route: ActivatedRoute,
    private dataSetService: DataSetService
  ) {
    this.id = this.route.snapshot.paramMap.get('id')!!;
    this.getData();
  }

  ngOnInit() {}

  getData() {
    this.dataSetService.getSingleDataSet(this.id).subscribe((data) => {
      let finalData = data[0].hasOwnProperty('row') ? data[0]['row'] : data;

      let dataChildren = finalData;

      if (dataChildren.length > 100) {
        dataChildren = dataChildren.slice(0, 100);
      }
      this.itemKeys = Object.keys(finalData[0]);
      this.selectedKey = this.itemKeys[0];
      this.selectedValue = this.itemKeys[0];

      this.itemValues = finalData.map((obj: any) => Object.values(obj));

      this.treeMapData = {
        name: 'Root',
        children: dataChildren,
      };
    });
  }

  onSubmit() {
    this.createBarChart(this.treeMapData);
    this.createTreemap(this.treeMapData);
  }

  private createBarChart(treeMapData: any) {
    const nativeElement = document.getElementById('barchart')!;
    const width = nativeElement.clientWidth;
    const height = nativeElement.clientHeight;

    const svg = d3
      .select(nativeElement)
      .append('svg')
      .attr('width', width)
      .attr('height', height);

    const filteredData = treeMapData.children;

    let keys = Object.keys(filteredData[0]);

    const xScale = d3
      .scaleBand()
      .domain(filteredData.map((d: any) => d[this.selectedKey]))
      .range([0, width])
      .padding(0.1);

    const maxValue =
      d3.max(filteredData, (d: any) => Number(d[this.selectedValue])) || 0;
    const yScale = d3.scaleLinear().domain([0, maxValue]).range([height, 0]);

    svg
      .selectAll('rect')
      .data(filteredData)
      .enter()
      .append('rect')
      .attr('x', (d: any) => xScale(d[this.selectedKey])!)
      .attr('y', (d: any) => yScale(Number(d[this.selectedValue])))
      .attr('width', xScale.bandwidth())
      .attr(
        'height',
        (d: any) => height - yScale(Number(d[this.selectedValue]))
      )
      .attr('fill', 'steelblue');

    svg
      .selectAll('text')
      .data(filteredData)
      .enter()
      .append('text')
      .text((d: any) => `${d[this.selectedKey]} (${d[this.selectedValue]})`)
      .attr(
        'x',
        (d: any) => xScale(d[this.selectedKey])! + xScale.bandwidth() / 2
      )
      .attr('y', (d: any) => yScale(Number(d[this.selectedValue])) - 5)
      .attr('text-anchor', 'middle')
      .style('fill', 'black');
  }

  createTreemap(treeMapData: any) {
    const nativeElement = document.getElementById('treemap')!;
    const width = nativeElement.clientWidth;
    const height = nativeElement.clientHeight;

    const treemapLayout = d3
      .treemap()
      .size([width, height])
      .padding(1)
      .round(true);

    const root = d3
      .hierarchy(treeMapData)
      .sum((d: any) => d[this.selectedValue]) // Update this line to use the correct key for the value property
      .sort((a, b) => (b.value || 0) - (a.value || 0)); // Update this line to use the correct key for the value property

    treemapLayout(root);

    const svg = d3
      .select(nativeElement)
      .append('svg')
      .attr('width', width)
      .attr('height', height);

    const nodes = svg
      .selectAll('rect')
      .data(root.leaves())
      .enter()
      .append('g')
      .attr('transform', (d: any) => `translate(${d.x0}, ${d.y0})`);

    nodes
      .append('rect')
      .attr('width', (d: any) => d.x1 - d.x0)
      .attr('height', (d: any) => d.y1 - d.y0)
      .style('fill', 'steelblue');

    nodes
      .append('text')
      .attr('dx', 4)
      .attr('dy', 14)
      .text((d: any) => d.data[this.selectedKey]) // Update this line to use the correct key for the name property
      .style('fill', 'black');
  }
}
