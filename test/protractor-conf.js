exports.config = {
  allScriptsTimeout: 11000,

  specs: [
    'e2e/features/*.feature'
  ],

  capabilities: {
    'browserName': 'chrome'
  },

  baseUrl: 'http://localhost:8081/',

  framework: 'cucumber',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  }
};
