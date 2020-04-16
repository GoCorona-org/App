import React from 'react';
import { TouchableOpacity } from 'react-native';

const SvgIcon = ({ svg, size, color, onPress }) => {
    const SVG = svg;
    return <TouchableOpacity onPress={onPress}>
        <SVG width={size} fill={color} height={size}></SVG>
    </TouchableOpacity>
}

export default SvgIcon