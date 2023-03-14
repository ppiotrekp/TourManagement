import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-single-user',
  templateUrl: './single-user.component.html',
  styleUrls: ['./single-user.component.css']
})
export class SingleUserComponent implements OnInit{
  // @ts-ignore
  public user: User;
  // @ts-ignore
  public roles: Role[];
  // @ts-ignore
  userId: bigint
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
    // @ts-ignore
    this.getUser(this.route.snapshot.paramMap.get('id'));
  }

  public getUser(id: bigint) {
    this.userService.getUser(id).subscribe(
      (response: User) => {
        this.user = response;
        this.roles = response.roles;
        // @ts-ignore
        console.log((this.roles))
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
