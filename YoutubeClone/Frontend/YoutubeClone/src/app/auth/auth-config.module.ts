import { NgModule } from '@angular/core';
import { AuthModule,  } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-zxvq56joab67xxqu.us.auth0.com',
            redirectUrl: window.location.origin,
            clientId: 'Be6oqW7krl0JnhlXpdRP1sny32JOAQ9O',
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}