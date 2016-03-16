INSTALLING Grunt
----------------
To install Grunt, you must first download and install node.js. Then, from the command line:
1. Install grunt-cli globally with: 'npm install -g grunt-cli'.
2. Navigate to the root /bootstrap/ directory (in our case mSites.admin/src/main/webapp/static/admin/bootstrap-3.3.2), and run 'npm install'. 
   npm will look at the package.json file and automatically install the necessary local dependencies listed there. When completed, you'll be 
   able to run the various Grunt commands provided from the command line. 'npm install' generates node_modules folder which we should exclude
   from our Git repository due to it's large size.

Grunt COMMANDS
--------------
1. grunt dist (Just compile CSS and JavaScript)
2. grunt watch (Watch)
3. grunt test (Run tests)
4. grunt docs (Build & test the docs assets)
5. grunt (Build absolutely everything and run tests)

Read http://getbootstrap.com/getting-started/ and http://gruntjs.com/getting-started for more info about installing and using Grunt.

mSites LESS FILES
-----------------
Until we'll find some time to create a Bootstrap Theme, we can stick to the following logic: mSites.admin/src/main/webapp/static/admin/bootstrap/
holds the Bootstrap resources we downloaded from their site. We use them for the /admin pages and as a possible fallback (hopefully we won't need
it). The Grunt project created as described above can be used to generate the site specific Bootstrap files. For example we can create a root 
file for Ladylike.gr in /admin/bootstrap-3.3.2/less/ladylike/ (ladylike.less), which just imports all required files of this directory. Before 
proceeding with 'grunt dist' we just have to import ladylike.less file at the bottom of bootstrap.less file. The files that Grunt generates in 
/dist folder should be moved to a permanent folder since /dist folder is regenerated every time we execute 'grunt dist'.
