
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Note} from './Note';

@Injectable({
  providedIn: 'root'
})
export class HttpService{
  private readonly apiUrl = 'http://localhost:8080/api/note';

  constructor(private http: HttpClient){ }

  getData() {
    return this.http.get<Note[]>(this.apiUrl);
  }

  getDataById(id: number) {
    return this.http.get<Note>(`${this.apiUrl}/${id}`);
  }

  updateNote(id: number, note: Note) {
    return this.http.put<Note>(`${this.apiUrl}/${id}`, note);
  }

  deleteNote(id: number){
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  postData(note: Note){
    const body = {name: note.name, description: note.description};
    return this.http.post<Note>(this.apiUrl, body);
  }
}
