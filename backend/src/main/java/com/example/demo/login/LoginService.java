package com.example.demo.login;

import com.example.demo.admin.Admin;
import com.example.demo.admin.AdminRepository;
//import com.example.demo.email.EmailService;
import com.example.demo.user.Users;
import com.example.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.email.EmailService;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;


    private String generateCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return Integer.toString(code);
    }

    public boolean findUserByEmailAndPassword(String email, String password) {
        List<Users> userList = userRepository.findAll();

        for (int i=0; i < userList.size(); i++) {
            if(userList.get(i).getEmail().equals(email) && userList.get(i).getPassword().equals(password)) {
                //TODO email send() einfügen

               // sendEmail(userList.get(i).getEmail(), )

                return true;
            }
        }
        return false;
    }

    public boolean findAdminByEmailAndPassword(String email, String password) {
        List<Admin> adminList = adminRepository.findAll();

        for (int i=0; i < adminList.size(); i++) {
            if(adminList.get(i).getEmail().equals(email) && adminList.get(i).getPassword().equals(password)) {
                //TODO email send() einfügen
                return true;
            }
        }
        return false;
    }

}
