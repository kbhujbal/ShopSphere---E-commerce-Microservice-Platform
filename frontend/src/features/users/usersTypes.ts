export interface UserDTO {
  id: string;
  username: string;
  email: string;
  password?: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  roles: string[];
}
