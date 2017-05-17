curl \
-F "status=2" \
-F "notify=0" \
-F "ipa=@/outputs/apk/app-debug.apk" \
-H "X-HockeyAppToken: 44dc3597af6b41379d8995f9abab4968" \
https://rink.hockeyapp.net/api/2/apps/5d02926c41e848588f31c3b62969782d/app_versions/upload