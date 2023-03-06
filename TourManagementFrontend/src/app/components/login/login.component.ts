import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit{
  username = '';
  password = '';
  invalidLogin = false;
  isLoggedIn = false;

  constructor(private loginService: LoginService, private router: Router, private storageService: StorageService) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn() && this.invalidLogin) {
      this.isLoggedIn = true;
    }
  }

  login() {
    let payload = {username: this.username, password: this.password};
    this.loginService.login(payload).subscribe((data) => {
        this.storageService.saveJwt(data);
        this.router.navigate(['/transits']);
        this.invalidLogin = false;
        this.isLoggedIn = true;
        console.log(data)
      },
        error => {
        this.invalidLogin = true;
        alert("bad credentials")
      })
  }
}
