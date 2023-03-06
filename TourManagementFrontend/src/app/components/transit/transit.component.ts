import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TransitService} from "../../service/transit.service";
import {Transit} from "../../model/transit";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-transit',
  templateUrl: './transit.component.html',
  styleUrls: ['./transit.component.css']
})
export class TransitComponent implements OnInit {
  // @ts-ignore
  public transits: Transit[];
  page: number = 0;

  constructor(private transitService: TransitService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getTransits();
    console.log("A")
  }

  nextPage() {
    this.page++;
    this.getTransits();
  }

  previousPage() {
    this.page--;
    this.getTransits();
  }

  public getTransits() {
    this.transitService.getTransits(this.page).subscribe(
      (response: Transit[]) => {
        this.transits = response;
        // this.transits.forEach((a:any) => {
        //   Object.assign(a,{quantity:1,total:a.price});
        // });
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
