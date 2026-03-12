# Play Store Content Rating Questionnaire Notes

## Target Audience
- **Age range**: 3–6 years (toddlers and preschoolers)
- **Primary audience**: Children

## Store Category
- **Category**: Education
- **Subcategory**: Educational Games

## Content Ratings
- **ESRB**: Everyone (E)
- **PEGI**: PEGI 3
- **IARC**: 3+
- **USK**: 0
- **ClassInd**: Livre (Free for all ages)

## Google Families Policy
- **Designed for Children**: Yes
- **Designed for Families**: Yes
- **Teacher Approved**: Apply after launch
- Complies with Google Play Families Policy requirements

## Content Questionnaire Answers
- **Violence**: None — No violent content of any kind
- **Sexual content**: None
- **Language**: None — No profanity or inappropriate language
- **Controlled substances**: None
- **Fear/Horror**: None
- **User interaction**: None — Children cannot communicate with others
- **Location sharing**: None — No location data collected
- **User-generated content**: None — No UGC features

## In-App Purchases
- **Present**: Yes (subscription for ad-free experience)
- **Accessible by children**: No — All purchases are behind a parental gate (math challenge)
- **Digital goods**: Ad removal subscription only

## Advertising
- **Ads present**: Yes (in free version)
- **Ad type**: COPPA-compliant, child-directed
- **Ad content rating**: Maximum G rating
- **Ad SDK**: Google AdMob with child-directed treatment enabled (`tagForChildDirectedTreatment`)
- **Ad interactions**: Behind parental gate — children cannot click through to external content
- **Ad personalization**: Disabled for child users

## Data Collection & Privacy
- **COPPA compliant**: Yes — Fully compliant with Children's Online Privacy Protection Act
- **Personal data from children**: None collected
- **Analytics**: Firebase Analytics with child-directed settings enabled
- **Crash reporting**: Firebase Crashlytics (no PII collected)
- **Privacy policy URL**: Required before submission (add to app and store listing)

## Notes for Submission
1. Ensure privacy policy URL is set in both the app and Play Console
2. Enable "Designed for Families" in Play Console → Store presence → App content
3. Select "Children" as target audience in Play Console
4. Complete the Data Safety section accurately
5. Set ad content rating to "G" maximum in AdMob console
6. Verify `tagForChildDirectedTreatment(true)` is set in AdMob initialization
