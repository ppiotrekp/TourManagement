import {Component, OnInit} from '@angular/core';
import {MyHistoryService} from "../../service/my-history.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {MyHistory} from "../../model/my-history";
import {HttpErrorResponse} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit{
  // @ts-ignore
  public users: User[];
  page: number = 0;
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private storageService: StorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      this.email = this.storageService.getDecodedJwt().sub;
      // @ts-ignore
      this.roles = this.storageService.getDecodedJwt().roles;
      // @ts-ignore
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    }
    this.getUsers();
  }

  public getUsers() {
    this.userService.getUsers(this.page).subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  nextPage() {
    this.page++;
    this.getUsers();
  }

  previousPage() {
    this.page--;
    this.getUsers();
  }

}
