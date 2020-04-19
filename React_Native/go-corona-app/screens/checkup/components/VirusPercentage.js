import React from 'react';

import V10 from '../../../assets/images/virus/10.svg'
import V40 from '../../../assets/images/virus/40.svg'
import V50 from '../../../assets/images/virus/50.svg'
import V60 from '../../../assets/images/virus/60.svg'
import V70 from '../../../assets/images/virus/70.svg'
import V80 from '../../../assets/images/virus/80.svg'
import V90 from '../../../assets/images/virus/90.svg'
import V100 from '../../../assets/images/virus/100.svg'

const VirusPercentageSVG = (props) => {
    const percentage = props.result;
    const getSvg = () => {
        switch (Math.floor(percentage / 10)) {
            case 1:
                return V10;
            case 2:
                return V10;
            case 3:
                return V10;
            case 4:
                return V40;
            case 5:
                return V50;
            case 6:
                return V60;
            case 7:
                return V70;
            case 8:
                return V80;
            case 9:
                return V90;
            case 10:
                return V100;
            default:
                return V10;
        }
    }
    let VSVG = getSvg();
    return <VSVG {...props}></VSVG>

}

export default VirusPercentageSVG;