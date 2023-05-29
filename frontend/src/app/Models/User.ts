import { UserRole } from './Role';

export class User {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  birthday: string;
  profilePicture: string;
  address: string;
  phone: string;
  role: UserRole = UserRole.USER;

  constructor(
    id: number = 0,
    firstname: string = '',
    lastname: string = '',
    email: string = '',
    birthday: string = '',
    profilePicture: string = '',
    address: string = '',
    phone: string = '',
    role: UserRole = UserRole.USER
  ) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.birthday = birthday;
    this.profilePicture = profilePicture;
    this.address = address;
    this.phone = phone;
    this.role = role;
  }
}
