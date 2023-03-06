import {Component, OnInit} from '@angular/core';
import {Transit} from "../../model/transit";
import {TransitService} from "../../service/transit.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {StorageService} from "../../service/storage.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-single-transit',
  templateUrl: './single-transit.component.html',
  styleUrls: ['./single-transit.component.css']
})
export class SingleTransitComponent implements OnInit{
  // @ts-ignore
  transit: Transit
  // @ts-ignore
  userId: number
  // @ts-ignore
  transitId: number
  // @ts-ignore
  numberOfSeats: number
  isLoggedIn = false;
  // @ts-ignore
  email: string

  constructor(private transitService: TransitService,
              private router: Router,
              private route: ActivatedRoute,
              private storageService: StorageService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      this.email = this.storageService.getDecodedJwt().sub;
      // @ts-ignore
    }
    // @ts-ignore
    this.getTransit(this.route.snapshot.paramMap.get('id'))
    this.getUserId(this.email)
  }

  public getTransit(id: bigint): void {
    this.transitService.getTransit(id).subscribe(
      (response: Transit) => {
        console.log(response);
        this.transit = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getUserId(username: string): void {
    this.userService.getId(username).subscribe(
      (response: any) => {
        console.log(response);
        this.userId = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public bookTransit(): void {
    this.transitId = 65
    this.userId = 9
    this.numberOfSeats = 2
    console.log(this.transitId)
    console.log(this.userId)
    let payload = {userId: this.userId, scheduleId: this.transitId, numberOfSeats: this.numberOfSeats};
    console.log(payload)
    this.transitService.bookTransit(payload).subscribe((data) => {
        alert("A")
        // this.router.navigate(['/transits']);
        console.log(data)
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        alert("A")
      }
    );
  }
}
