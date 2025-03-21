import {Component, inject, NgModule} from '@angular/core';
import {Router, RouterModule, RouterOutlet} from '@angular/router';
import {data} from 'browserslist';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Note} from '../Note';
import {HttpService} from '../http.service';
import {NgModel} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {HomeComponent} from '../home/home.component';



@Component({
  selector: "my-app",
  standalone: true,
  imports: [HttpClientModule, FormsModule],
  styleUrl: './about.component.css',
  templateUrl: './about.component.html',
  providers: [HttpService]
})

export class AboutComponent {


  note: Note = new Note("", "");

  receivedNote: Note | undefined;
  done: boolean = false;

  constructor(private httpService: HttpService, private router: Router) {
  }


  goToPage(pageName: string): void {
    this.router.navigate([`${pageName}`]);
  }

  submit(note: Note) {
    this.httpService.postData(note)
      .subscribe({
        next: (data: any) => {
          this.receivedNote = data;
          this.done = true;
        },
        error: error => console.log(error)
      });
  }
}









