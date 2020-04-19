import { MaterialCommunityIcons } from '@expo/vector-icons';
import * as Location from 'expo-location';
import React, { Component } from 'react';
import { ActivityIndicator, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import MapView, { PROVIDER_GOOGLE } from 'react-native-maps';
import Button from '../components/button/Button';
import GooglePlacesInput from '../components/map/Places';
import Point from '../components/map/Point';
import Popup, { PopupStyles } from '../components/popup/Popup';
import { csoptsApi } from '../constants/AppSettings';
import Http from '../services/Http';

// TODO: move to constants
const latitudeDelta = 0.2;
const longitudeDelta = 0.1;

export default class HomeScreen extends Component {
  state = {
    loading: true,
    region: null,
    points: [],
    error: null,
    popupVisibility: true
  }
  map = null;
  async componentDidMount() {
    let { status } = await Location.requestPermissionsAsync();
    if (status !== 'granted') {
      // TODO: move to string constants
      this.setState({ error: 'Location permission is needed' })
      return;
    }
    const location = await Location.getCurrentPositionAsync({});
    const { latitude, longitude } = location.coords;
    const cspotsResponse = await Http.get(csoptsApi);
    var points = this.getHeatMapPoints(cspotsResponse.data);
    this.setHeatMapPoints(latitude, longitude, points);
  }

  onMapReady = () => {
    this.map.animateToRegion(this.state.region);
  }

  setHeatMapPoints(latitude, longitude, points) {
    this.setState({
      region: {
        latitude,
        longitude,
        latitudeDelta,
        longitudeDelta
      },
      loading: false,
      points
    });
  }

  getHeatMapPoints(data) {
    return data;
  }

  onRegionChangeComplete = (region) => {
    this.setState({
      region
    })
  }
  onLocationSelect = (location) => {
    const region = {
      latitude: location.lat,
      longitude: location.lng,
      latitudeDelta,
      longitudeDelta
    }
    setTimeout(() => {
      this.map.animateToRegion(region);
    }, 0);
  }

  togglePopup() {
    const { popupVisibility } = this.state
    this.setState({ popupVisibility: !popupVisibility })
  }

  render() {
    const { loading, points, region } = this.state;
    return (
      <View style={styles.container}>
        {this.state.loading ?
          <ActivityIndicator></ActivityIndicator> :
          <>
            <View style={styles.searchInput}>
              <GooglePlacesInput onLocationSelect={this.onLocationSelect}></GooglePlacesInput>
            </View>
            <MapView
              maxZoomLevel={14}
              showsBuildings={true}
              ref={(ref) => { this.map = ref }}
              provider={PROVIDER_GOOGLE}
              onMapReady={this.onMapReady}
              showsUserLocation={true}
              showsMyLocationButton={false}
              onRegionChangeComplete={this.onRegionChangeComplete}
              style={[styles.map]}
            >
              {points.map((i, key) =>
                <Point point={i} region={region} key={key}></Point>
              )}
            </MapView>
            {this.renderShowLocationButton()}
            <Popup isVisible={this.state.popupVisibility}>
              <Text style={PopupStyles.question}>{"Are you currently suffering from any kind of illness ?"}</Text>
              <Button onPress={() => this.togglePopup()} label={"I'm not sure"} style={PopupStyles.button1} labelStyle={PopupStyles.buttonlabel1} />
              <Button onPress={() => this.togglePopup()} label={"Mark myself safe"} style={PopupStyles.button2} labelStyle={PopupStyles.buttonlabel2} />
            </Popup>
          </>
        }
      </View>
    );
  }

  goToCurrentPoition = async () => {
    const location = await Location.getCurrentPositionAsync({});
    this.map.animateToRegion({ ...location.coords, latitudeDelta, longitudeDelta });
  }

  renderShowLocationButton = () => {
    return (
      <TouchableOpacity
        style={styles.myLocationButton}
        onPress={() => {
          this.goToCurrentPoition()
        }}
      >
        <MaterialCommunityIcons name='crosshairs-gps' size={24} />
      </TouchableOpacity>
    )
  }
}

HomeScreen.navigationOptions = {
  header: null,
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  searchInput: {
    zIndex: 1000,
    maxWidth: 400,
    backgroundColor: '#fff',
    width: '92%',
    position: 'absolute',
    top: 20,
    left: '4%',
    elevation: 3,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 14
  },
  myLocationButton: {
    backgroundColor: '#fff',
    position: 'absolute',
    bottom: 10,
    right: 10,
    padding: 15,
    elevation: 3,
    alignItems: 'center',
    alignSelf: 'flex-end',
    justifyContent: 'center',
    borderRadius: 50
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});

