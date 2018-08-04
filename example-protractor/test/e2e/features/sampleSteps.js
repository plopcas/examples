var sampleSteps = function() {

	this.Given(/^this is the first sample$/, function (callback) {
	  console.log("\n----" + this.prop);
	  callback();
	});

	this.Given(/^this is the second sample$/, function (callback) {
	  this.greetings("everybody", callback);
	});

};

module.exports = sampleSteps;