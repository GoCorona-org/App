import React from 'react';
import { View, StyleSheet, Text, ActivityIndicator } from 'react-native'
import { RectButton, ScrollView } from 'react-native-gesture-handler'

import Colors from '../../constants/Colors';

function CustomButton({ label, width, Icon, loading, disabled, onPress }) {
    return (
        <RectButton
            style={{ ...styles.option, width, backgroundColor: disabled ? '#c1c1c1' : '#e4dfdf' }}
            onPress={disabled ? null : onPress}>
            {loading ? 
                <ActivityIndicator size="small" color={Colors.tabIconSelected} /> : 
                <View style={styles.optionContainer}>
                    {Icon ? (
                        <View>
                            <Icon width={25} height={25} />
                        </View>
                    ) : null}
                    {<View style={styles.textContainer}>
                        <Text style={styles.optionText}>{label}</Text>
                    </View>}
                </View>
            }
        </RectButton>
    )
}

export default CustomButton;

const styles = StyleSheet.create({
    option: {
        backgroundColor: '#e4dfdf',
        paddingHorizontal: 15,
        paddingVertical: 15,
        borderWidth: StyleSheet.hairlineWidth,
        borderBottomWidth: 0,
        borderColor: '#ededed',
        borderRadius: 5,
        margin: 10

    },
    optionContainer: {
        flexDirection: 'row',
        alignItems: "center",
    },
    textContainer: {
        marginLeft: 10,
    },
    optionText: {
        fontSize: 15,
        alignSelf: 'flex-start',
        marginTop: 1,
        textAlign: "center",
        flexWrap: "wrap"
    },
});