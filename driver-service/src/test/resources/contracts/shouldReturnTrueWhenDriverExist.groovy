package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return true when driver exist"

    request {
        method GET()
        url '/api/v1/drivers/exist/c86fbf65-7aaf-4f7e-8cb6-e8ddeacabb1f'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Boolean.TRUE.toString())
    }
}