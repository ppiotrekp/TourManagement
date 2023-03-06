import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit{
  email = '';
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
    let payload = {email: this.email, password: this.password};
    this.loginService.login(payload).subscribe((data: any) => {
        this.storageService.saveJwt(data);
        this.router.navigate(['/dishes']);
        this.invalidLogin = false;
        this.isLoggedIn = true;
        console.log(data)
      },
        (error: any) => {
        this.invalidLogin = true;
        alert("bad credentials")
      })
  }
}
