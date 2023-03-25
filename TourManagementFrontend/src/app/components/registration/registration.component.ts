import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {RegistrationService} from "../../service/registration.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{
  user:User =new User();
  // @ts-ignore
  userError: User;
  isCreated:boolean = false;
  userExists:boolean=false;
  passwordsDifferent:boolean = true;


  constructor(private registrationService: RegistrationService, private router: Router, private storageService: StorageService) { }

  register(){
    this.comparePasswords();
    this.registrationService.registerUser(this.user).subscribe(data=>{
      this.user = new User();
      this.isCreated = true;
      alert("Registered successfully")
      this.router.navigate(['/login'])
    },error=> {
      this.userError=error.error;
      this.isCreated = false;
    });
  }

  comparePasswords() {
    var password = (document.getElementById("password") as HTMLInputElement).value;
    var repeatPassword = (document.getElementById("password1") as HTMLInputElement).value;
    if (password != repeatPassword) {
      this.passwordsDifferent = false;
    }

  }

  ngOnInit(): void {
  }

}
