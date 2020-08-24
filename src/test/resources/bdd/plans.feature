Feature: Health Card API regressions

  Scenario Outline: Delete all plans
    Given A HTTP DELETE request <testcase> on <endPoint> returning status code <responseCode>
    Examples:
      | testcase     | endPoint  | responseCode |
      | delete plans | /v1/plans | 200          |

  Scenario Outline: Create plan0
    Given A HTTP POST request to <testcase> with body <requestBodyPath> on <endPoint> returning status code <responseCode>
    Then Validate HTTP Response object <expectedOutputJSONPath>
    Examples:
      | testcase    | requestBodyPath             | endPoint  | responseCode | expectedOutputJSONPath       |
      | create plan | testdata/request/plan0.json | /v1/plans | 200          | testdata/response/plan0.json |

  Scenario Outline: Get plan for id
    Given A HTTP GET request <testcase> to get plan for id <id> on <endPoint> returning status code <responseCode>
    Then Validate HTTP Response List <expectedOutputJSONPath>
    Examples:
      | testcase        | id | endPoint  | responseCode | expectedOutputJSONPath       |
      | get plan for id | 0  | /v1/plans | 200          | testdata/response/plan1.json |

  Scenario Outline: Create plan1
    Given A HTTP POST request to <testcase> with body <requestBodyPath> on <endPoint> returning status code <responseCode>
    Then Validate HTTP Response object <expectedOutputJSONPath>
    Examples:
      | testcase    | requestBodyPath             | endPoint  | responseCode | expectedOutputJSONPath       |
      | create plan | testdata/request/plan1.json | /v1/plans | 200          | testdata/response/plan1.json |

  Scenario Outline: Get plans
    Given A HTTP GET request <testcase> to get all plans on <endPoint> returning status code <responseCode>
    Then Validate HTTP Response List <expectedOutputJSONPath>
    Examples:
      | testcase        | endPoint  | responseCode | expectedOutputJSONPath        |
      | get all plans   | /v1/plans | 200          | testdata/response/plans1.json |