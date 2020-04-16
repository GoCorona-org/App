import React from 'react'
import { createIconSetFromIcoMoon } from '@expo/vector-icons';
import icoMoonConfig from './selection.json';
const expoAssetId = require("../../assets/fonts/spotcorona.ttf");
const IconMoon = createIconSetFromIcoMoon(icoMoonConfig, 'spotcorona', expoAssetId);

import Colors from '../../constants/Colors'

const Icon = ({ focused, ...rest }) => {
    let color = rest.color;
    if (!color) {
        color = focused ? Colors.tabIconSelected : Colors.tabIconDefault
    }
    return (
        <IconMoon {...rest} color={color} />
    );
};

export default Icon;