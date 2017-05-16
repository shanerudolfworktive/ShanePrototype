curl \
-F "status=2" \
-F "notify=0" \
-F "ipa=app/build/outputs/apk/app-debug.apk" \
-H "X-HockeyAppToken: 4bcd149148c3120b3b94951e4fdc59ad" \
https://rink.hockeyapp.net/api/2/apps/5d02926c41e848588f31c3b62969782d/app_versions/upload