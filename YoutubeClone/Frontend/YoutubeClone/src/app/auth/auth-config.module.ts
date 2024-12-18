import { NgModule } from '@angular/core';
import { AuthModule,  } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-zxvq56joab67xxqu.us.auth0.com',
            redirectUrl: 'http://localhost:4200',
            clientId: 'Be6oqW7krl0JnhlXpdRP1sny32JOAQ9O',
            scope: 'openid profile offline_access email',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes:["http://localhost:8080"],
            customParamsAuthRequest:{
				audience: "http://localhost:8080"
			}
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}