import { Routes } from '@angular/router';
import {AboutComponent} from '../about/about.component';
import {HomeComponent} from '../home/home.component';
import {EditComponent} from '../edit/edit.component';


export const routes: Routes = [
  {path:'about',component: AboutComponent},
  {path:'home',component: HomeComponent},
  { path: 'edit/:id', component: EditComponent }

];
