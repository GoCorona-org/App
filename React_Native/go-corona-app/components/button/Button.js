import React from 'react';
import { Text, StyleSheet, TouchableOpacity } from 'react-native';

//Todo: Add Icon Button
class Button extends React.Component {
    render() {
        const { label, style, onPress, disabled, labelStyle } = this.props;
        return (
            <TouchableOpacity style={[styles.container, { backgroundColor: disabled ? "#c1c1c1" : "#E72E68" }, style]} onPress={onPress}>
                <Text style={[styles.label, labelStyle]}>{label}</Text>
            </TouchableOpacity>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        alignItems: "center",
        justifyContent: "center",
        paddingHorizontal: 15,
        paddingVertical: 10,
        borderRadius: 4
    },
    label: {
        fontSize: 14,
        lineHeight: 20
    }
})

export default Button;