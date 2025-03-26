import {Component,OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {HttpClient} from '@angular/common/http';
import {Note} from '../Note';
import { FormsModule } from '@angular/forms';
import {MatDialog,MatDialogModule} from '@angular/material/dialog';
import {DialologyComponent} from '../dialog_note_delete/dialology.component';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatFormFieldModule,MatInputModule,MatButtonModule,MatTableModule,FormsModule,MatDialogModule,MatInputModule,MatFormFieldModule],
  styleUrl: './home.component.css',
  templateUrl: './home.component.html',
  providers: [HttpService]
})

export class HomeComponent implements OnInit {
  dataSource: MatTableDataSource<Note> = new MatTableDataSource();
  constructor(
    private router: Router,
    public dialog: MatDialog,
    public httpService: HttpService
  ) {
  }

  ngOnInit(): void {
    this.httpService.getData()
      .subscribe((notes: Note[]) => {
        this.dataSource = new MatTableDataSource(notes);
        console.log(notes);
      });
  }

  openDeleteDialog(item: Note): void {
    const dialogRef = this.dialog.open(DialologyComponent, {
      data: { name: item.name }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteItem(item.id);
        console.log(result);
      }
    });
  }

  goToPage(pageName: string): void {
    this.router.navigate([`${pageName}`]);
  }

  goToEdit(id: number): void {
    this.router.navigate(['/edit', id]);  // Переход с параметром ID
  }

  deleteItem(id: number) {
    this.httpService.deleteNote(id)
      .subscribe(() => {
        this.ngOnInit();
      });
  }
}



