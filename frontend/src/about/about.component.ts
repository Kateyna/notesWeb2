import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {Note} from '../Note';
import {HttpService} from '../http.service';

@Component({
  selector: "my-app",
  standalone: true,
  imports: [FormsModule],
  styleUrl: './about.component.css',
  templateUrl: './about.component.html',
  providers: [HttpService]
})

export class AboutComponent {
  note: Note = new Note(0,"", "");
  receivedNote: Note | undefined;
  done: boolean = false;
  constructor(private httpService: HttpService, private router: Router) {
  }

  submit(note: Note) {
    const name = note.name?.trim();
    const description = note.description?.trim();
    if (!name || !description) {
      alert('Пожалуйста, заполните все поля!');
      return;
    }

    this.httpService.postData(note)
      .subscribe({
        next: (data: any) => {
          this.receivedNote = data;
          this.done = true;
          this.router.navigate(['/home']);
          console.log(Inject(data));
        },
      });
  }
}









