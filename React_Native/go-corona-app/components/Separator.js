import React from "react"
import { View, StyleSheet } from 'react-native'
import Colors from '../constants/Colors'

export default function Separator() {
    return <View style={styles.separator} />;
}

const styles = StyleSheet.create({
    separator: {
        borderBottomColor: Colors.tabIconDefault,
        borderBottomWidth: StyleSheet.hairlineWidth,
    },
});