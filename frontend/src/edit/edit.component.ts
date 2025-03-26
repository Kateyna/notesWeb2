import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpService} from '../http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import { Note } from '../Note';

@Component({
  selector: 'app-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css',
  providers: [HttpService]
})
export class EditComponent implements OnInit{
  note: Note = new Note(0,"", "");
  id!: number;
  constructor(
    private httpService: HttpService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.loadNote(this.id);
    }
  }

  loadNote(id: number): void {
    this.httpService.getDataById(id)
      .subscribe({
        next: (data) => {
          this.note = data;
          console.log(data);
        },
      });
  }

  editItem(): void {
    const name = this.note.name?.trim();
    const description = this.note.description?.trim();
    if (!name || !description) {
      alert('Пожалуйста, заполните все поля!');
      return;
    }
    this.httpService.updateNote(this.id,this.note)
      .subscribe({
        next: () => {
          console.log();
          this.router.navigate(['/home']);
        }
      });
  }
}
