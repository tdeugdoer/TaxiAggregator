package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return true when passenger exist"

    request {
        method GET()
        url '/api/v1/passengers/exist/ce268ddc-6b47-4e0f-86c0-bd0f936160f9'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Boolean.TRUE.toString())
    }
}