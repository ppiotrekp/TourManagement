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
import { UserComponent } from './components/user/user.component';
import { SingleUserComponent } from './components/single-user/single-user.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SingleBusComponent } from './components/single-bus/single-bus.component';
import { SingleBusTransitsComponent } from './components/single-bus-transits/single-bus-transits.component';

const appRoute: Routes = [
  {path : '',redirectTo : 'home',pathMatch : 'full'},
  {path: 'home', component:HomeComponent},
  {path: 'login', component: LoginComponent },
  {path: 'transits', component: TransitComponent },
  {path: 'transits/:id', component: SingleTransitComponent },
  {path: 'history', component: MyHistoryComponent },
  {path: 'users', component: UserComponent },
  {path: 'users/:id', component: SingleUserComponent },
  {path: 'registration', component: RegistrationComponent },
  {path: 'buses', component: BusComponent },
  {path: 'buses/:id', component: SingleBusComponent },
  {path: 'buses/:id/transits', component: SingleBusTransitsComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    BusComponent,
    HomeComponent,
    LoginComponent,
    TransitComponent,
    SingleTransitComponent,
    MyHistoryComponent,
    UserComponent,
    SingleUserComponent,
    RegistrationComponent,
    SingleBusComponent,
    SingleBusTransitsComponent
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
