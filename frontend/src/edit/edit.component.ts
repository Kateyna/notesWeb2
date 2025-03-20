import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpService} from '../http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import { Note } from '../Note';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css',
  providers: [HttpService]
})
export class EditComponent implements OnInit{

  note: Note = new Note("", "");
  id: number | null = null; // Используем null для проверки

  constructor(
    private httpService: HttpService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) { }


  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.loadNote(this.id);
    }
  }

  loadNote(id: number): void {
    this.http.get<Note>(`http://localhost:8080/api/note/${id}`)
      .subscribe({
        next: (data) => {
          this.note = data;
        },
      });
  }

  editItem(): void {
    this.http.put(`http://localhost:8080/api/note/${this.id}`, this.note)
      .subscribe({
        next: () => {
          this.router.navigate(['/home']);


        }
      });
  }
}
