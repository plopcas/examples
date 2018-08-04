import {NgModule, Component} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";

@Component({
    selector: 'my-app',
    template: ` <h1>{{title}}</h1>
                <h2>{{description}}</h2>
              
                <div class="row">
                    <div class="col-xs-4">
                        <input type="text" class="form-control" required name="name"/>
                    </div>
                    <div class="col-xs-4">
                        <button id="add-btn" class="btn btn-info" (click)="addPlayer()">Add</button>
                    </div>
                </div>
              `,
    styles: [``],
    inputs: ['name']

})

class AppComponent {
    title = 'ElasticRaffle';
    description = 'Add names to the list to begin';
    public name = '';

    addPlayer(){
        console.log('Player added')
    }

}

@NgModule({
    imports: [BrowserModule],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);