import { NgModule } from '@angular/core';
import { AuthModule,  } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-zkc612czg3cvko2r.us.auth0.com',
            redirectUrl: 'http://localhost:4200',
            clientId: 'JA3nxo4FrCMLMXwhUZjkM82I6wJal5s3',
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}