import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TransitService} from "../../service/transit.service";
import {Transit} from "../../model/transit";
import {HttpErrorResponse} from "@angular/common/http";
import {StorageService} from "../../service/storage.service";
import {NgForm} from "@angular/forms";
import {Bus} from "../../model/bus";

@Component({
  selector: 'app-transit',
  templateUrl: './transit.component.html',
  styleUrls: ['./transit.component.css']
})
export class TransitComponent implements OnInit {
  // @ts-ignore
  public transits: Transit[];
  page: number = 0;
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

  constructor(private transitService: TransitService,
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
    this.getTransits();
    console.log(this.showAdminBoard)
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
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  openModal() {
    const modalBody = document.querySelector("#my-modal")
    // @ts-ignore
    modalBody.style.display = 'block';
  }

  closeModal() {
    const modalBody = document.querySelector("#my-modal")
    // @ts-ignore
    modalBody.style.display = 'none';
  }

  public addTransit(addForm: NgForm): void {
    // @ts-ignore
    let none = document.querySelector("#my-modal").style.display = 'none';
    this.transitService.addTransit(addForm.value).subscribe(
      (response: Transit) => {
        console.log(response);
        this.getTransits();
        addForm.reset();
        none;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        none;
      }
    );
  }
}
