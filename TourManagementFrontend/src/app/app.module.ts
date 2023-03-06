import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BusComponent } from './components/bus/bus.component';
import { HomeComponent } from './components/home/home.component';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import { TransitComponent } from './components/transit/transit.component';
import { SingleTransitComponent } from './components/single-transit/single-transit.component';
import { MyHistoryComponent } from './components/my-history/my-history.component';

const appRoute: Routes = [
  {path : '',redirectTo : 'home',pathMatch : 'full'},
  {path: 'home', component:HomeComponent},
  {path: 'login', component: LoginComponent },
  {path: 'transits', component: TransitComponent },
  {path: 'transits/:id', component: SingleTransitComponent },
  {path: 'history', component: MyHistoryComponent },
]

@NgModule({
  declarations: [
    AppComponent,
    BusComponent,
    HomeComponent,
    LoginComponent,
    TransitComponent,
    SingleTransitComponent,
    MyHistoryComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoute),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
