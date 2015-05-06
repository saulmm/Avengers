package saulmm.avengers.model.rest;

import retrofit.RequestInterceptor;

public class MarvelApiController {

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    RequestInterceptor authorizationInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {

            String marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);
            request.addEncodedQueryParam(MarvelApi.PARAM_API_KEY, publicKey);
            request.addEncodedQueryParam(MarvelApi.PARAM_HASH, marvelHash);
        }
    };
}
