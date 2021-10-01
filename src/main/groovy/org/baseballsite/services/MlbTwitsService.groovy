package org.baseballsite.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MlbTwitsService {
    final Logger log = LoggerFactory.getLogger(this.class)

    def users = ['mlbtwits', 'admin', 'user1', 'user2', 'user3']

    def getUsers() {
        return users
    }
}
