#!/bin/bash
# bump-version.sh
# Script to bump version in app/build.gradle.kts

if [ -z "$1" ]; then
    echo "Usage: $0 <new_version>"
    echo "Example: $0 1.0.1"
    exit 1
fi

NEW_VERSION=$1
BUILD_GRADLE="app/build.gradle.kts"

if [ ! -f "$BUILD_GRADLE" ]; then
    echo "Error: $BUILD_GRADLE not found!"
    exit 1
fi

# Increment versionCode
CURRENT_VERSION_CODE=$(grep "versionCode = " "$BUILD_GRADLE" | awk '{print $3}')
NEW_VERSION_CODE=$((CURRENT_VERSION_CODE + 1))

# Update versionCode
sed -i "s/versionCode = $CURRENT_VERSION_CODE/versionCode = $NEW_VERSION_CODE/" "$BUILD_GRADLE"

# Update versionName
sed -i "s/versionName = \".*\"/versionName = \"$NEW_VERSION\"/" "$BUILD_GRADLE"

echo "✅ Version updated successfully:"
echo "   Version Code: $NEW_VERSION_CODE"
echo "   Version Name: $NEW_VERSION"
