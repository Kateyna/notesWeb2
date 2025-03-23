import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {MatTable, MatTableDataSource, MatTableModule} from '@angular/material/table';

import { AboutComponent} from '../about/about.component';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatInput, MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Note} from '../Note';
import { FormsModule } from '@angular/forms';
import {MatDialog, MatDialogActions, MatDialogContent, MatDialogModule, MatDialogTitle} from '@angular/material/dialog';
import {DialologyComponent} from '../dialology/dialology.component';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatFormFieldModule,MatInputModule,MatButtonModule,MatTableModule,HttpClientModule,FormsModule,MatDialogModule,MatInputModule,MatFormFieldModule],
  styleUrl: './home.component.css',
  templateUrl: './home.component.html'


})

export class HomeComponent implements OnInit {
  dataSource: MatTableDataSource<Note> = new MatTableDataSource();

  constructor(
    private router: Router,
    private http: HttpClient,
  private route: ActivatedRoute,
  public dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.http.get<Note[]>('http://localhost:8080/api/note')
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

    this.http.delete(`http://localhost:8080/api/note/${id}`)
      .subscribe(() => {
        this.ngOnInit();
      });
  }

}



