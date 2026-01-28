import { useState } from 'react';
import { useGetShipmentByTrackingQuery, useUpdateShipmentStatusMutation } from '../../features/shipments/shipmentsApi';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import Badge from '../../components/ui/Badge';
import Select from '../../components/ui/Select';
import Card from '../../components/ui/Card';
import { formatDate } from '../../utils/formatDate';

const shipmentStatuses = ['PENDING', 'PICKED_UP', 'IN_TRANSIT', 'DELIVERED', 'FAILED'];

export default function ShipmentsPage() {
  const [tracking, setTracking] = useState('');
  const [searchTracking, setSearchTracking] = useState('');
  const { data: shipment, isFetching } = useGetShipmentByTrackingQuery(searchTracking, { skip: !searchTracking });
  const [updateStatus] = useUpdateShipmentStatusMutation();

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Shipments</h1>

      <div className="flex gap-3 mb-8">
        <Input
          placeholder="Enter tracking number..."
          value={tracking}
          onChange={(e) => setTracking(e.target.value)}
          className="max-w-sm"
        />
        <Button onClick={() => setSearchTracking(tracking)} disabled={!tracking}>
          Search
        </Button>
      </div>

      {isFetching && <p className="text-sm text-gray-500">Searching...</p>}

      {shipment && (
        <Card className="p-6 max-w-2xl">
          <div className="grid grid-cols-2 gap-4 text-sm">
            <div><p className="text-gray-500">Tracking #</p><p className="font-medium">{shipment.trackingNumber}</p></div>
            <div><p className="text-gray-500">Carrier</p><p className="font-medium">{shipment.carrier}</p></div>
            <div><p className="text-gray-500">Service</p><p className="font-medium">{shipment.serviceType}</p></div>
            <div>
              <p className="text-gray-500">Status</p>
              <Badge>{shipment.status || 'PENDING'}</Badge>
            </div>
            <div><p className="text-gray-500">Est. Delivery</p><p className="font-medium">{formatDate(shipment.estimatedDeliveryDate)}</p></div>
            <div><p className="text-gray-500">Destination</p><p className="font-medium">{shipment.destinationCity}, {shipment.destinationState}</p></div>
          </div>
          <div className="mt-4 flex items-center gap-3">
            <Select
              label="Update Status"
              options={shipmentStatuses.map((s) => ({ value: s, label: s }))}
              value={shipment.status || 'PENDING'}
              onChange={(e) => updateStatus({ id: shipment.id, status: e.target.value })}
              className="w-48"
            />
          </div>
        </Card>
      )}

      {searchTracking && !isFetching && !shipment && (
        <p className="text-sm text-gray-500">No shipment found for this tracking number.</p>
      )}
    </div>
  );
}
