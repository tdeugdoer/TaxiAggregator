package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return false when passenger not exist"

    request {
        method GET()
        url '/api/v1/passengers/exist/11111111-1111-1111-1111-111111111111'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Boolean.FALSE.toString())
    }
}
