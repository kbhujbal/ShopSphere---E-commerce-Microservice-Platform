import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useGetProductByIdQuery, useCreateProductMutation, useUpdateProductMutation } from '../../features/products/productsApi';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import Spinner from '../../components/ui/Spinner';

export default function ProductFormPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const { data: existing, isLoading } = useGetProductByIdQuery(id!, { skip: !isEdit });
  const [createProduct, { isLoading: creating }] = useCreateProductMutation();
  const [updateProduct, { isLoading: updating }] = useUpdateProductMutation();

  const [form, setForm] = useState({
    name: '', description: '', category: '', price: '', stockQuantity: '',
    brand: '', sku: '', active: true, images: '', tags: '', specifications: '',
  });
  const [error, setError] = useState('');

  useEffect(() => {
    if (existing) {
      setForm({
        name: existing.name,
        description: existing.description,
        category: existing.category,
        price: String(existing.price),
        stockQuantity: String(existing.stockQuantity),
        brand: existing.brand,
        sku: existing.sku,
        active: existing.active,
        images: existing.images?.join(', ') || '',
        tags: existing.tags?.join(', ') || '',
        specifications: existing.specifications?.join('\n') || '',
      });
    }
  }, [existing]);

  const set = (field: string) => (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) =>
    setForm({ ...form, [field]: e.target.value });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    const body = {
      name: form.name,
      description: form.description,
      category: form.category,
      price: parseFloat(form.price),
      stockQuantity: parseInt(form.stockQuantity, 10),
      brand: form.brand,
      sku: form.sku,
      active: form.active,
      images: form.images ? form.images.split(',').map((s) => s.trim()).filter(Boolean) : [],
      tags: form.tags ? form.tags.split(',').map((s) => s.trim()).filter(Boolean) : [],
      specifications: form.specifications ? form.specifications.split('\n').map((s) => s.trim()).filter(Boolean) : [],
    };
    try {
      if (isEdit) {
        await updateProduct({ id: id!, body }).unwrap();
      } else {
        await createProduct(body).unwrap();
      }
      navigate('/admin/products');
    } catch {
      setError('Failed to save product.');
    }
  };

  if (isEdit && isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  return (
    <div className="max-w-2xl">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">
        {isEdit ? 'Edit Product' : 'New Product'}
      </h1>
      {error && <div className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-600">{error}</div>}
      <form onSubmit={handleSubmit} className="space-y-4 rounded-xl border border-gray-200 bg-white p-6">
        <Input label="Name" value={form.name} onChange={set('name')} required />
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea value={form.description} onChange={set('description')} rows={3} required
            className="block w-full rounded-lg border border-gray-300 px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500" />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <Input label="Category" value={form.category} onChange={set('category')} required />
          <Input label="Brand" value={form.brand} onChange={set('brand')} required />
        </div>
        <div className="grid grid-cols-3 gap-4">
          <Input label="Price" type="number" step="0.01" value={form.price} onChange={set('price')} required />
          <Input label="Stock" type="number" value={form.stockQuantity} onChange={set('stockQuantity')} required />
          <Input label="SKU" value={form.sku} onChange={set('sku')} required />
        </div>
        <Input label="Images (comma-separated URLs)" value={form.images} onChange={set('images')} />
        <Input label="Tags (comma-separated)" value={form.tags} onChange={set('tags')} />
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Specifications (one per line)</label>
          <textarea value={form.specifications} onChange={set('specifications')} rows={3}
            className="block w-full rounded-lg border border-gray-300 px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500" />
        </div>
        <label className="flex items-center gap-2">
          <input type="checkbox" checked={form.active} onChange={(e) => setForm({ ...form, active: e.target.checked })} className="rounded border-gray-300" />
          <span className="text-sm text-gray-700">Active</span>
        </label>
        <div className="flex gap-3">
          <Button type="submit" disabled={creating || updating}>
            {creating || updating ? 'Saving...' : isEdit ? 'Update Product' : 'Create Product'}
          </Button>
          <Button type="button" variant="ghost" onClick={() => navigate('/admin/products')}>Cancel</Button>
        </div>
      </form>
    </div>
  );
}
