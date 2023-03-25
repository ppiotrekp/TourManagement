import {Role} from "./Role";

export class User {
  id!:bigint;
  firstName!:string;
  lastName!:string;
  username!:string;
  password!:string;
  phoneNumber!:string;
  enabled!:boolean;
  locked!:boolean;
  subscribed!: boolean;
  roles!:Role[];
}
