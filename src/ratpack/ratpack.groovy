import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.baseballsite.services.BaseballService
import org.baseballsite.services.MlbStatsAPIService
import org.baseballsite.services.MlbTwitsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode
import static ratpack.jackson.Jackson.jsonNode
import static ratpack.jackson.Jackson.jsonNode

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    serverConfig {
    }

    bindings {
        bind BaseballService
        bind MlbStatsAPIService
    }

    handlers { BaseballService baseballService, MlbStatsAPIService mlbStatsAPIService ->
        all RequestLogger.ncsa(log)

        all {
            String forwardedProto = 'X-Forwarded-Proto'
            if (request.headers.contains(forwardedProto)
                    && request.headers.get(forwardedProto) != 'https') {
                redirect(301, "https://${request.headers.get('Host')}${request.rawUri}")
            } else {
                next()
            }
        }

        all {
            response.headers.add('Access-Control-Allow-Origin', '*')
            response.headers.add('Access-Control-Allow-Headers', 'Authorization, origin, x-requested-with, content-type')
            response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
            next()
        }

        get('ping') {
            render '<3'
        }

        prefix('mlbtwits') {
            prefix('api/v1/') {
                path('users') {
                    byMethod {
                        get {
                            def users = mlbTwitsService.getUsers()
                            render json(users)
                        }
                    }
                }

//                path('users/:username') {
//                    def username = pathTokens['username']
//                    byMethod {
//                        get {
//                            def user = mlbTwitsService.getUser(username)
//                            render json(user)
//                        }
//                    }
//                }
//
//                path('users/:username/tweets') {
//                    def username = pathTokens['username']
//                    byMethod {
//                        get {
//                            def user = mlbTwitsService.getUser(username)
//                            def tweets = mlbTwitsService.getTweetsByUser(user)
//                            render json(tweets)
//                        }
//
//                        post {
//                            parse(jsonNode()).map { params ->
//                                log.info(params.toString())
//                                def message = params.get('message')?.textValue()
//                                def userId = params.get('userId')?.intValue()
//
//                                assert message
//                                assert userId
//
//                                mlbTwitsService.tweet(userId.toString(), message)
//                            }.then { Tweet tweet ->
//                                println "created tweet with id: " + tweet.getTweetId()
//                                render json(tweet)
//                            }
//                        }
//                    }
//                }
//
//                path('teams') {
//                    byMethod {
//                        get {
//                            def teams = mlbTwitsService.getTeams()
//                            render json(teams)
//                        }
//                    }
//                }
//
//                path('teams/:teamId') {
//                    def teamId = pathTokens['teamId']
//                    byMethod {
//                        get {
//                            def team = mlbTwitsService.getTeam(teamId)
//                            render json(team)
//                        }
//                    }
//                }
//
//                path('teams/:teamId/roster') {
//                    def teamId = pathTokens['teamId']
//                    byMethod {
//                        get {
//                            def teamRoster = mlbTwitsService.getTeamRoster(teamId)
//                            render json(teamRoster)
//                        }
//                    }
//                }
//
//                path('players/info') {
//                    byMethod {
//                        get {
//                            def players = mlbTwitsService.getPlayersWithTeams()
//                            render json(players)
//                        }
//                    }
//                }
//
//                path('players') {
//                    byMethod {
//                        get {
//                            def players = mlbTwitsService.getPlayers()
//                            render json(players)
//                        }
//                    }
//                }
//
//                path('players/:playerId') {
//                    def playerId = pathTokens['playerId']
//                    byMethod {
//                        get {
//                            def player = mlbTwitsService.getPlayer(playerId)
//                            render json(player)
//                        }
//                    }
//                }
//
//                path('players/:playerId/tweets') {
//                    def playerId = pathTokens['playerId']
//                    byMethod {
//                        get {
//                            def tweets = mlbTwitsService.getTweets(playerId)
//                            render json(tweets)
//                        }
//                        post {
//                            parse(jsonNode()).map { params ->
//                                def message = params.get('message')?.textValue()
//                                def userId = params.get('userId')?.intValue()\
//
//                                assert message
//                                assert userId
//
//                                mlbTwitsService.tweet(playerId, userId.toString(), message)
//                            }.then { Tweet tweet ->
//                                println "created tweet with id: " + tweet.getTweetId()
//                                render json(tweet)
//                            }
//                        }
//                    }
//                }
//
//                path('playerLabels') {
//                    byMethod {
//                        get {
//                            def players = mlbTwitsService.getPlayers()
//                            render json(players.collect { ['playerName' : it.playerName, 'label' : it.playerNamePlain]})
//                        }
//                    }
//                }
//
//                path('tweets') {
//                    byMethod {
//                        get {
//                            def tweets = mlbTwitsService.getTweets()
//                            render json(tweets)
//                        }
//
//                        post {
//                            parse(jsonNode()).map { params ->
//                                def message = params.get('message')?.textValue()
//                                def userId = params.get('userId')?.intValue()
//                                assert message
//                                assert userId
//
//                                List<Tweet> insertedRecords = mlbTwitsService.tweet(userId.toString(), message)
//                            }.then { List<Tweet> insertedRecords ->
//                                render json(insertedRecords)
//                            }
//                        }
//                    }
//                }
            }
        }

        prefix('api/v1') {
            prefix('fantasy-baseball') {
                get('mlb/teams') {
                    def mlbTeams = mlbStatsAPIService.getMlbTeams()

                    render new JsonBuilder(mlbTeams).toPrettyString()
                }

                get('mlb/team/rosters') {
                    def mlbTeamId = request.queryParams.mlbTeamId

                    if (mlbTeamId) {
                        def teamRosters = mlbStatsAPIService.getMlbRoster(mlbTeamId)

                        render new JsonBuilder(teamRosters).toPrettyString()
                    } else {
                        clientError(400)
                    }
                }

                get('mlb/team/prospects') {
                    def mlbTeamId = request.queryParams.mlbTeamId

                    if (mlbTeamId) {
                        def teamRosters = mlbStatsAPIService.getMlbRoster(mlbTeamId)

                        render new JsonBuilder(teamRosters).toPrettyString()
                    } else {
                        clientError(400)
                    }
                }

                get('mlb/player/stats/hitting') {
                    def mlbPlayerId = request.queryParams.mlbPlayerId

                    if (mlbPlayerId) {
                        def hittingStats = mlbStatsAPIService.getPlayerHittingStats(mlbPlayerId)

                        render new JsonBuilder(hittingStats).toPrettyString()
                    } else {
                        clientError(400)
                    }
                }

                get('mlb/player/stats/pitching') {
                    def mlbPlayerId = request.queryParams.mlbPlayerId

                    if (mlbPlayerId) {
                        def pitchingStats = mlbStatsAPIService.getPlayerPitchingStats(mlbPlayerId)

                        render new JsonBuilder(pitchingStats).toPrettyString()
                    } else {
                        clientError(400)
                    }
                }
            }
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}
