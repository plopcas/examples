"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const platform_browser_1 = require("@angular/platform-browser");
const platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
let AppComponent = class AppComponent {
    constructor() {
        this.title = 'ElasticRaffle';
        this.description = 'Add names to the list to begin';
        this.name = '';
    }
    addPlayer() {
        console.log('Player added');
    }
};
AppComponent = __decorate([
    core_1.Component({
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
    }), 
    __metadata('design:paramtypes', [])
], AppComponent);
let AppModule = class AppModule {
};
AppModule = __decorate([
    core_1.NgModule({
        imports: [platform_browser_1.BrowserModule],
        declarations: [AppComponent],
        bootstrap: [AppComponent]
    }), 
    __metadata('design:paramtypes', [])
], AppModule);
platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(AppModule);
//# sourceMappingURL=main.js.map