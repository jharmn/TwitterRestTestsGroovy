Feature: Twitter user lookup

@Positive @Get @User
Scenario: Show twitter user
	Given I access the resource url "/1/users/show.json"
        And I provide parameter "screen_name" as "jasonh_n_austin"
        And I provide parameter "include_entities" as "true"
	When I retrieve the results
	Then the status code should be 200
	And it should have the field "name" containing the value "Jason Harmon"
	And it should have the field "id" containing the value "57005215"

@Negative @Get @User
Scenario: Invalid twitter user
	Given I access the resource url "/1/users/show.json"
        And I provide parameter "screen_name" as "jasonh_n_portland"
        And I provide parameter "include_entities" as "true"
	When I retrieve the results
	Then the status code should be 404
