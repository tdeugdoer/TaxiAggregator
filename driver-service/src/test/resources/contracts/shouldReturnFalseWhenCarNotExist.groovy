package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return false when cars not exist"

    request {
        method GET()
        url '/api/v1/cars/exist/11111111'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Boolean.FALSE.toString())
    }
}
