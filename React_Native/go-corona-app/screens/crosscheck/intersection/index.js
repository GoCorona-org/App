import * as React from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';

import StepList from '../../../components/stepper/StepList';
import Step from '../../../components/stepper/Step';
import Introduction from './Introduction';
import Terms from './Terms';
import UploadData from './UploadData'

export default function IntersectionScreen() {
  return (
    <View style={styles.container}>
      <StepList>
        <Step component={Introduction} />
        <Step component={Terms} />
        <Step component={UploadData} />
      </StepList>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'flex-start'
  },
});