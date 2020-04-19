
import React from 'react'
import { Marker } from 'react-native-maps';
import { FontAwesome as FAIcon } from '@expo/vector-icons';
import Colors from '../../constants/Colors';
import Icon from '../icon/Icon';

const Point = (props) => {
    return <Marker coordinate={props.point}>
        <Icon color={getColor(props.point.status || props.point.degree)} name='home' size={25}></Icon>
    </Marker>
}

const getColor = (degree) => {
    switch (degree) {
        case 1:
            return 'red';
        case 2:
            return 'gray';
        case 0:
            return 'green';
        default:
            return 'green';
    }
}

const getIcon = (zoom) => {
    let size = 48;
    let name = 'spot-concern';
    return { name, size }
}

export default Point;