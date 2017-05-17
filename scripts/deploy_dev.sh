
echo "Deployment Started!"

APK_PATH=$1

echo $APK_PATH

ACCESS_TOKEN=$2

echo $ACCESS_TOKEN

APP_ID=$3

echo $APP_ID

curl \
-F "status=2" \
-F "notify=0" \
-F "ipa=@$APK_PATH" \
-H "X-HockeyAppToken:$ACCESS_TOKEN" \
https://rink.hockeyapp.net/api/2/apps/$APP_ID/app_versions/upload

echo "Deployment finished!"